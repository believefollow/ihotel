jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DSpczService } from '../service/d-spcz.service';
import { IDSpcz, DSpcz } from '../d-spcz.model';

import { DSpczUpdateComponent } from './d-spcz-update.component';

describe('Component Tests', () => {
  describe('DSpcz Management Update Component', () => {
    let comp: DSpczUpdateComponent;
    let fixture: ComponentFixture<DSpczUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dSpczService: DSpczService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DSpczUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DSpczUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DSpczUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dSpczService = TestBed.inject(DSpczService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dSpcz: IDSpcz = { id: 456 };

        activatedRoute.data = of({ dSpcz });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dSpcz));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dSpcz = { id: 123 };
        spyOn(dSpczService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dSpcz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dSpcz }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dSpczService.update).toHaveBeenCalledWith(dSpcz);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dSpcz = new DSpcz();
        spyOn(dSpczService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dSpcz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dSpcz }));
        saveSubject.complete();

        // THEN
        expect(dSpczService.create).toHaveBeenCalledWith(dSpcz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dSpcz = { id: 123 };
        spyOn(dSpczService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dSpcz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dSpczService.update).toHaveBeenCalledWith(dSpcz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
