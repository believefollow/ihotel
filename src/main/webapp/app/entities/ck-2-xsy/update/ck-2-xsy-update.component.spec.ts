jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { Ck2xsyService } from '../service/ck-2-xsy.service';
import { ICk2xsy, Ck2xsy } from '../ck-2-xsy.model';

import { Ck2xsyUpdateComponent } from './ck-2-xsy-update.component';

describe('Component Tests', () => {
  describe('Ck2xsy Management Update Component', () => {
    let comp: Ck2xsyUpdateComponent;
    let fixture: ComponentFixture<Ck2xsyUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ck2xsyService: Ck2xsyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Ck2xsyUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(Ck2xsyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Ck2xsyUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ck2xsyService = TestBed.inject(Ck2xsyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const ck2xsy: ICk2xsy = { id: 456 };

        activatedRoute.data = of({ ck2xsy });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ck2xsy));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ck2xsy = { id: 123 };
        spyOn(ck2xsyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ck2xsy });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ck2xsy }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ck2xsyService.update).toHaveBeenCalledWith(ck2xsy);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ck2xsy = new Ck2xsy();
        spyOn(ck2xsyService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ck2xsy });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ck2xsy }));
        saveSubject.complete();

        // THEN
        expect(ck2xsyService.create).toHaveBeenCalledWith(ck2xsy);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ck2xsy = { id: 123 };
        spyOn(ck2xsyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ck2xsy });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ck2xsyService.update).toHaveBeenCalledWith(ck2xsy);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
