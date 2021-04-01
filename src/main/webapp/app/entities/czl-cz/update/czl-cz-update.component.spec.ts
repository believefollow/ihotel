jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CzlCzService } from '../service/czl-cz.service';
import { ICzlCz, CzlCz } from '../czl-cz.model';

import { CzlCzUpdateComponent } from './czl-cz-update.component';

describe('Component Tests', () => {
  describe('CzlCz Management Update Component', () => {
    let comp: CzlCzUpdateComponent;
    let fixture: ComponentFixture<CzlCzUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let czlCzService: CzlCzService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CzlCzUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CzlCzUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CzlCzUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      czlCzService = TestBed.inject(CzlCzService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const czlCz: ICzlCz = { id: 456 };

        activatedRoute.data = of({ czlCz });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(czlCz));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czlCz = { id: 123 };
        spyOn(czlCzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czlCz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: czlCz }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(czlCzService.update).toHaveBeenCalledWith(czlCz);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czlCz = new CzlCz();
        spyOn(czlCzService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czlCz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: czlCz }));
        saveSubject.complete();

        // THEN
        expect(czlCzService.create).toHaveBeenCalledWith(czlCz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czlCz = { id: 123 };
        spyOn(czlCzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czlCz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(czlCzService.update).toHaveBeenCalledWith(czlCz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
