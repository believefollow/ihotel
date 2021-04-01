jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CzBqzService } from '../service/cz-bqz.service';
import { ICzBqz, CzBqz } from '../cz-bqz.model';

import { CzBqzUpdateComponent } from './cz-bqz-update.component';

describe('Component Tests', () => {
  describe('CzBqz Management Update Component', () => {
    let comp: CzBqzUpdateComponent;
    let fixture: ComponentFixture<CzBqzUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let czBqzService: CzBqzService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CzBqzUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CzBqzUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CzBqzUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      czBqzService = TestBed.inject(CzBqzService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const czBqz: ICzBqz = { id: 456 };

        activatedRoute.data = of({ czBqz });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(czBqz));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czBqz = { id: 123 };
        spyOn(czBqzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czBqz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: czBqz }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(czBqzService.update).toHaveBeenCalledWith(czBqz);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czBqz = new CzBqz();
        spyOn(czBqzService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czBqz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: czBqz }));
        saveSubject.complete();

        // THEN
        expect(czBqzService.create).toHaveBeenCalledWith(czBqz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czBqz = { id: 123 };
        spyOn(czBqzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czBqz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(czBqzService.update).toHaveBeenCalledWith(czBqz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
